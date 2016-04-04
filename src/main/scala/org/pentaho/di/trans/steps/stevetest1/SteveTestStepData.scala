package org.pentaho.di.trans.steps.stevetest1

import org.pentaho.di.core.row.RowMetaInterface
import org.pentaho.di.trans.step.{StepDataInterface, BaseStepData}

class SteveTestStepData extends BaseStepData with StepDataInterface {

  var outputMeta: RowMetaInterface = null

}
