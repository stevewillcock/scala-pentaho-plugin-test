package plugin.test

import org.pentaho.di.core.row.RowMetaInterface
import org.pentaho.di.trans.step.{BaseStepData, StepDataInterface}

class TemplateStepData() extends BaseStepData() with StepDataInterface {
  var outputRowMeta: RowMetaInterface = _

  def TemplateStepData() {}
}
