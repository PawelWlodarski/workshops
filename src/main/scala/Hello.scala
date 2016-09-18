import java.io.File

object Hello {
  def main(args: Array[String]): Unit = {
    val filesHere = (new File(".")).listFiles()

    for (file <- filesHere
         if !file.getName.endsWith(".bat")
         if file.isFile
    )
      println(file)
  }

  class JugMeeting(title: String, participants: Int) {
    println("this is constructor")
  }

}
