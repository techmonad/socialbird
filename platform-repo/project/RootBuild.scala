import sbt.Keys._
import sbt._

object RootBuild extends Build {

  // configure prompt to show current project
  override lazy val settings = super.settings :+ {
    shellPrompt := { s => "[SB PROJECT] " + Project.extract(s).currentProject.id + " > " }
  }

}
