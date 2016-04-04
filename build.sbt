import java.io.File

name := "pentaho-gpload-automater"

version := "1.0"

scalaVersion := "2.11.7"

resolvers += "pentaho-releases" at "http://repository.pentaho.org/artifactory/repo/"
resolvers += "maven-eclipse-repo" at "http://maven-eclipse.github.io/maven"

val swtVersion = {
  val os = (sys.props("os.name"), sys.props("os.arch")) match {
    case ("Linux", _) => "gtk.linux.x86"
    case ("Mac OS X", "amd64" | "x86_64") => "cocoa.macosx.x86_64"
    case ("Mac OS X", _) => "cocoa.macosx.x86"
    case (operatingSystem, "amd64") if operatingSystem.startsWith("Windows") => "win32.win32.x86_64"
    case (operatingSystem, _) if operatingSystem.startsWith("Windows") => "win32.win32.x86"
    case (operatingSystem, arch) => sys.error("Cannot obtain lib for OS '" + operatingSystem + "' and architecture '" + arch + "'")
  }

  "org.eclipse.swt." + os

}

val pentahoVersion = "6.1.0.0-99"

libraryDependencies ++= Seq(
  "pentaho-kettle" % "kettle-core" % pentahoVersion % "provided",
  "pentaho-kettle" % "kettle-ui-swt" % pentahoVersion % "provided",
  "org.apache.commons" % "commons-vfs2" % "2.1-20150824" % "provided",
  "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
  "org.eclipse.swt" % swtVersion % "4.3.2" % "provided"
)

assemblyExcludedJars in assembly := {
  val cp = (fullClasspath in assembly).value
  println(cp)
  val xs = cp filter { x => !x.data.getName.contains("kettle-") }
  println(xs)
  xs
}

scalacOptions += "-deprecation"

val deployTask = TaskKey[Unit]("deploy", "Copies jar to pentaho directory")

deployTask <<= packageBin in Compile map { (pck) =>
  val local = pck.getPath
  IO.copyFile(new File(local), new File("""C:\\work\misc\pentaho-kettle\dist\plugins\steve-step-test-1\pentaho-gpload-automater-assembly-1.0.jar"""))
}

