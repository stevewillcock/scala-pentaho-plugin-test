package plugin.test

import java.util

import org.eclipse.swt.widgets.Shell
import org.pentaho.di.core.database.DatabaseMeta
import org.pentaho.di.core.exception.{KettleException, KettleXMLException}
import org.pentaho.di.core.row.value.ValueMetaString
import org.pentaho.di.core.row.{RowMetaInterface, ValueMetaInterface}
import org.pentaho.di.core.variables.VariableSpace
import org.pentaho.di.core.xml.XMLHandler
import org.pentaho.di.core.{CheckResult, CheckResultInterface, Const, Counter}
import org.pentaho.di.i18n.BaseMessages
import org.pentaho.di.repository.{ObjectId, Repository}
import org.pentaho.di.trans.step.{BaseStepMeta, StepDataInterface, StepDialogInterface, StepInterface, StepMeta, StepMetaInterface}
import org.pentaho.di.trans.{Trans, TransMeta}
import org.w3c.dom.Node

class TemplateStepMeta extends BaseStepMeta with StepMetaInterface {
  // for i18n purposes
  private var outputField: String = ""

  def TemplateStepMeta() {}

  override def getXML: String = "<outputField>" + getOutputField + "</outputField>" + Const.CR

  def getOutputField: String = outputField

  override def getFields(r: RowMetaInterface, origin: String, info: Array[RowMetaInterface], nextStep: StepMeta, space: VariableSpace) = {
    val v = new ValueMetaString
    v.setName(outputField)
    v.setTrimType(ValueMetaInterface.TRIM_TYPE_BOTH)
    v.setOrigin(origin)
    r.addValueMeta(v)
  }

  override def clone(): Object = super.clone()

  override def loadXML(stepNode: Node, databases: util.List[DatabaseMeta], counters: util.Map[String, Counter]): Unit = {
    try {
      setOutputField(XMLHandler.getNodeValue(XMLHandler.getSubNode(stepNode, "outputField")))
    } catch {
      case e: Exception => throw new KettleXMLException("Template Plugin Unable to read step info from XML node", e)
    }
  }

  def setOutputField(outputField: String) = {
    this.outputField = outputField
  }

  def setDefault() = this.outputField = "template_outfield"

  override def check(remarks: util.List[CheckResultInterface], transMeta: TransMeta, stepMeta: StepMeta, prev: RowMetaInterface, input: Array[String], output: Array[String], info: RowMetaInterface): Unit = {
    if (input.length > 0)
      remarks.add(new CheckResult(CheckResultInterface.TYPE_RESULT_OK, "Step is receiving info from other steps.", stepMeta))
    else
      remarks.add(new CheckResult(CheckResultInterface.TYPE_RESULT_ERROR, "No input received from other steps!", stepMeta))
  }

  def getDialog(shell: Shell, meta: StepMetaInterface, transMeta: TransMeta, name: String): StepDialogInterface =
    new TemplateStepDialog(shell, meta.asInstanceOf[BaseStepMeta], transMeta, name)

  def getStep(stepMeta: StepMeta, stepDataInterface: StepDataInterface, cnr: Int, transMeta: TransMeta, trans: Trans): StepInterface =
    new TemplateStep(stepMeta, stepDataInterface, cnr, transMeta, trans)

  def getStepData: StepDataInterface = new TemplateStepData()

  override def readRep(rep: Repository, id_step: ObjectId, databases: util.List[DatabaseMeta], counters: util.Map[String, Counter]) = {
    try {
      outputField = rep.getStepAttributeString(id_step, "outputField") //$NON-NLS-1$
    } catch {
      case e: Exception => throw new KettleException(BaseMessages.getString(PKG, "TemplateStep.Exception.UnexpectedErrorInReadingStepInfo"), e)
    }
  }

  override def saveRep(rep: Repository, id_transformation: ObjectId, id_step: ObjectId) = {
    try {
      rep.saveStepAttribute(id_transformation, id_step, "outputField", outputField) //$NON-NLS-1$
    } catch {
      case e: Exception => throw new KettleException(BaseMessages.getString(PKG, "TemplateStep.Exception.UnableToSaveStepInfoToRepository") + id_step, e)
    }
  }

  def PKG: Class[TemplateStepMeta] = classOf[TemplateStepMeta]

}


