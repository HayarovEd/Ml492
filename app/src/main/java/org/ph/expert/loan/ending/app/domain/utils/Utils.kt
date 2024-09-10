package org.ph.expert.loan.ending.app.domain.utils

import org.ph.expert.loan.ending.app.domain.model.BaseState
import org.ph.expert.loan.ending.app.domain.model.Loan
import org.ph.expert.loan.ending.app.domain.model.StatusApplication



fun String.setStatusByPush(
    loans:List<Loan>,
): StatusApplication {
    val subString = this.split("/")
    return when (subString.first()) {
        MESSAGE_LOANS -> {
            StatusApplication.Connect(BaseState.Loans)
        }

        else -> {
            StatusApplication.Connect(BaseState.Loans)
        }
    }
   /* if (subString.size == 1) {
        return when (subString.first()) {
            MESSAGE_LOANS -> {
                StatusApplication.Connect(BaseState.Loans)
            }

            else -> {
                StatusApplication.Connect(BaseState.Loans)
            }
        }
    } else {
        val position = subString.last().toInt()
        return when (subString.first()) {
            MESSAGE_LOANS -> {
                if (position<=loans.size-1) {
                    StatusApplication.Offer(
                        loans[position]
                    )
                } else {
                    StatusApplication.Connect(BaseState.Loans)
                }
            }

            else -> {
                StatusApplication.Offer(
                    loans[0]
                )
            }
        }

    }*/
}