package io.hydrosphere.mist.master.execution.workers.cleaner

import java.util.concurrent.Executors
import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.core.{DefaultDockerClientConfig, DockerClientBuilder}
import io.hydrosphere.mist.core.CommonData.WorkerInitInfo
import io.hydrosphere.mist.master._
import io.hydrosphere.mist.master.execution.workers.StopAction
import io.hydrosphere.mist.utils.Logger

import java.nio.file.Path
import scala.collection.JavaConverters._
import scala.concurrent.{ExecutionContext, Future}
import scala.util._
import java.util.concurrent.TimeUnit
import akka.actor.ActorLogging
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.lang.Runnable

import mist.api._
import scala.collection.JavaConverters._
import java.io.File

/*
* Removes leftover containers and volumes every x minutes.
* */
class DockerCleaner(dockerClient: DockerClient) extends Logger {
  private final val ex = new ScheduledThreadPoolExecutor(1)
  private var freeDiskSpace = 100

  val cleanupTask = new Runnable {
    def run(): Unit = {
      logger.info("Pruning docker volumes and containers!")

      try {
        dockerClient
          .listContainersCmd()
          .withStatusFilter("exited")
          .exec()
          .asScala.foreach { container =>
          logger.info(s"Pruning container with ID: ${container.getId()}")
          dockerClient.removeContainerCmd(container.getId()).exec()
        }

        dockerClient
          .listVolumesCmd()
          .withDanglingFilter(true)
          .exec().getVolumes()
          .asScala.foreach { volume =>
          logger.info(s"Pruning volume with name: ${volume.getName()}")
          dockerClient.removeVolumeCmd(volume.getName()).exec()
        }
      } catch {
        case e: Throwable => logger.error(e.getLocalizedMessage())
      }
    }
  }
  val diskSpaceTask = new Runnable {
    def run(): Unit = {
      try {
        val file = new File(".")
        val totalSpace = file.getTotalSpace
        val freeSpace = file.getFreeSpace
        val shareFree = (freeSpace.toFloat / totalSpace.toFloat * 100).round.toInt
        if(freeDiskSpace != shareFree) {
          logger.info(s"Free space on disk: ${freeSpace}/${totalSpace} - ${shareFree}%")
          freeDiskSpace = shareFree

          if(freeDiskSpace < 15) {
            //cleanupTask.run()
          }
        }
      } catch {
        case e: Throwable => {
          e.printStackTrace()
          logger.error(e.getLocalizedMessage())
        }
      }
    }
  }

  def start() {
    logger.info("Docker cleaner started!")

    ex.scheduleAtFixedRate(cleanupTask, 10, 60 * 60, TimeUnit.SECONDS)
    ex.scheduleAtFixedRate(diskSpaceTask, 10, 60 * 5, TimeUnit.SECONDS)
  }
}

object DockerCleaner {
  def create(config: DockerRunnerConfig): DockerCleaner = {
    import config._

    val client = {
      val config = DefaultDockerClientConfig.createDefaultConfigBuilder()
        .withDockerHost("unix:///var/run/docker.sock")
        .build()
      DockerClientBuilder.getInstance(config).build()
    }

    val cleaner = new DockerCleaner(client)
    cleaner.start()
    return cleaner
  }
}
