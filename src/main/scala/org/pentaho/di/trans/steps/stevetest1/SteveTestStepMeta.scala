package org.pentaho.di.trans.steps.stevetest1

import org.pentaho.di.core.annotations.Step
import org.pentaho.di.trans.step._
import org.pentaho.di.trans.{Trans, TransMeta}

@Step(
  id = "SteveTestStep",
  name = "SteveTestStep",
  image = "icon.svg",
  categoryDescription = "Utility"
)
class SteveTestStepMeta extends BaseStepMeta with StepMetaInterface {

  def getStep(stepMeta: StepMeta, stepDataInterface: StepDataInterface, cnr: Int, tr: TransMeta, trans: Trans): StepInterface =
    new SteveTestStep(stepMeta, stepDataInterface, cnr, tr, trans)

  def getStepData: StepDataInterface = new SteveTestStepData

  override def setDefault(): Unit = {}

}
