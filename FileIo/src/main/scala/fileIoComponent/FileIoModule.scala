package fileIoComponent

import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule

class FileIoModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[FileIOInterface].to[fileIoComponent.fileIoJsonImpl.FileIo]
    //bind[FileIOInterface].to[fileIoComponent.fileIoXmlImpl.FileIo]
  }
}
