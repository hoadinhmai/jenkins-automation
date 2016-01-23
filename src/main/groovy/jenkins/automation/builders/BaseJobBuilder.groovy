package jenkins.automation.builders

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job
import jenkins.automation.utils.CommonUtils

/**
 * The very first and basic building block
 *
 * <p>
 *      creates a job with colorized input,
 *      log rotator, email notifications and build claiming
 * </p>
 * @param name used to name the job
 * @param description job description
 * @param emails list of developer to get notifications
 * <p>
 *
 * @see <a href="https://github.com/imuchnik/jenkins-automation/blob/gh-pages/docs/examples.md#base-job-job-builder"
 *      target="_blank">Base job builder example</a>

 * </p>
 */
class BaseJobBuilder {
    String name
    String description
    List<String> emails

    Job build(DslFactory factory) {
        factory.job(name) {
            it.description this.description
            CommonUtils.addDefaults(delegate)
            publishers {
                if (emails) {
                    publishers {
                        extendedEmail(emails.join(',')) {
                            trigger(triggerName: 'Failure',
                                    sendToDevelopers: true, sendToRequester: false, includeCulprits: true, sendToRecipientList: true)
                            trigger(triggerName: 'Fixed',
                                    sendToDevelopers: true, sendToRequester: false, includeCulprits: true, sendToRecipientList: true)
                        }
                    }
                }
            }
        }
    }
}



