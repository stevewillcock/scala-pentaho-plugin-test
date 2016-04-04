package org.pentaho.di.trans.steps.stevetest1

import org.pentaho.di.core.row.RowMetaInterface

object RowMetaWrapperImplicits {

  implicit def RowMetaInterface2Wrapper(rmi: RowMetaInterface): RowMetaWrapper = new RowMetaWrapper(rmi)

  class RowMetaWrapper(val rmi: RowMetaInterface) {

    def klone: RowMetaInterface = {
      val cloneMethod = rmi.getClass.getMethods.filter(x => x.getName == "clone")
      cloneMethod(0).invoke(rmi).asInstanceOf[RowMetaInterface]
    }

  }

}
