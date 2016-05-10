package org.pentaho.di.trans.steps.stevetest1

import org.pentaho.di.core.RowMetaAndData
import org.pentaho.di.core.row.{RowDataUtil, ValueMetaInterface}
import org.pentaho.di.trans.step._
import org.pentaho.di.trans.steps.stevetest1.ClonableRowMetaInterfaceWrapperImplicits._
import org.pentaho.di.trans.{Trans, TransMeta}

class SteveTestStep(stepMeta: StepMeta, stepDataInterface: StepDataInterface, copyNr: Int, transMeta: TransMeta, trans: Trans) extends BaseStep(stepMeta, stepDataInterface, copyNr, transMeta, trans) with StepInterface {

  private val data: SteveTestStepData = stepDataInterface.asInstanceOf[SteveTestStepData]

  val dataToAppend = new RowMetaAndData
  dataToAppend.addValue("Test23", ValueMetaInterface.TYPE_STRING, "This is a test")

  override def processRow(smi: StepMetaInterface, sdi: StepDataInterface): Boolean = {

    Option(getRow) match {
      case None => false
      case Some(row) =>
        if (first) {
          first = false
          data.outputMeta = getInputRowMeta.createClone
          data.outputMeta.mergeRowMeta(dataToAppend.getRowMeta, getStepname)
        }
        putRow(data.outputMeta, RowDataUtil.addRowData(row, getInputRowMeta.size, dataToAppend.getData))
        true
    }

  }

}



