package org.pentaho.di.trans.steps.stevetest1

import org.pentaho.di.core.row.RowMetaInterface

object ClonableRowMetaWrapperImplicits {

  val cloneMethod = classOf[RowMetaInterface].getMethods.filter(x => x.getName == "clone")

  class ScalaClonableRowMetaInterfaceWrapper(rmi: RowMetaInterface) {
    def createClone = cloneMethod(0).invoke(rmi).asInstanceOf[RowMetaInterface]
  }

  implicit def scalaClonableRowMetaInterfaceWrapper(rowMetaInterface: RowMetaInterface): ScalaClonableRowMetaInterfaceWrapper = new ScalaClonableRowMetaInterfaceWrapper(rowMetaInterface)

}
