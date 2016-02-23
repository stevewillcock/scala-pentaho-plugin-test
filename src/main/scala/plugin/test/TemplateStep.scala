package plugin.test

import org.pentaho.di.core.Const
import org.pentaho.di.core.row.RowDataUtil
import org.pentaho.di.trans.step._
import org.pentaho.di.trans.{Trans, TransMeta}

class TemplateStep(s: StepMeta,
                   stepDataInterface: StepDataInterface,
                   c: Int,
                   t: TransMeta,
                   dis: Trans) extends BaseStep(s, stepDataInterface, c, t, dis) with StepInterface {

  private var data: TemplateStepData = _

  private var meta: TemplateStepMeta = _

  override def processRow(smi: StepMetaInterface, sdi: StepDataInterface): Boolean = {
    meta = smi.asInstanceOf[TemplateStepMeta]
    data = sdi.asInstanceOf[TemplateStepData]
    val r = getRow
    if (r == null) {
      setOutputDone()
      return false
    }
    if (first) {
      first = false
//      data.outputRowMeta = getInputRowMeta.clone
      meta.getFields(data.outputRowMeta, getStepname, null, null, this)
      logBasic("template step initialized successfully")
    }
    val outputRow = RowDataUtil.addValueData(r, data.outputRowMeta.size - 1, "dummy value")
    putRow(data.outputRowMeta, outputRow)
    if (checkFeedback(getLinesRead)) {
      logBasic("Line nr:" + getLinesRead)
    }
    true
  }

  override def init(smi: StepMetaInterface, sdi: StepDataInterface): Boolean = {
    meta = smi.asInstanceOf[TemplateStepMeta]
    data = sdi.asInstanceOf[TemplateStepData]
    super.init(smi, sdi)
  }

  override def dispose(smi: StepMetaInterface, sdi: StepDataInterface) {
    meta = smi.asInstanceOf[TemplateStepMeta]
    data = sdi.asInstanceOf[TemplateStepData]
    super.dispose(smi, sdi)
  }

  def run() {
    logBasic("Starting to run...")
    try {
      while (processRow(meta, data) && !isStopped) {
        println("hey")
      }
    } catch {
      case e: Exception =>
        logError("Unexpected error : " + e.toString)
        logError(Const.getStackTracker(e))
        setErrors(1)
        stopAll()
    } finally {
      dispose(meta, data)
      logBasic("Finished, processing " + getLinesRead + " rows")
      markStop()
    }
  }
}
