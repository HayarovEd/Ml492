package org.ph.expert.loan.ending.app.data.mapper

import org.ph.expert.loan.ending.app.data.remote.dto.server_data.BaseDto
import org.ph.expert.loan.ending.app.domain.model.AppConfig
import org.ph.expert.loan.ending.app.domain.model.BaseData
import org.ph.expert.loan.ending.app.domain.model.Loan

fun BaseDto.mapToBaseData(): BaseData {
    return BaseData(
        appConfig = AppConfig(
            privacyPolicyHtml = this.appConfigDto.privacyPolicyHtml,
            showCalculatorItem = this.appConfigDto.showCalculatorItem,
            showChat = this.appConfigDto.showChat,
            showNewsItem = this.appConfigDto.showNewsItem,
            showPhoneAuthentication = this.appConfigDto.showPhoneAuthentication,
            userTermHtml = this.appConfigDto.userTermHtml
        ),
        loans = this.loanDtos.map {
            Loan(
                screen = it.screen,
                name = it.name,
                order = it.order,
                amounts = it.summPrefix + " " + it.summMin + " " + it.summMid + " " + it.summMax + " " + it.summPostfix,
                description = it.description,
                hidePercentFields = it.hidePercentFields,
                hideTermFields = it.hideTermFields,
                id = it.id,
                itemId = it.itemId,
                orderButtonText = it.orderButtonText,
                percent = it.percentPrefix + " " + it.percent + " " + it.percentPostfix,
                position = it.position,
                score = it.score,
                showCash = it.showCash,
                showQiwi = it.showQiwi,
                showVisa = it.showVisa,
                showMir = it.showMir,
                showMastercard = it.showMastercard,
                showYandex = it.showYandex,
                term = it.termPrefix + " " + it.termMin + " " + it.termMid + " " + it.termMax + " " + it.termPostfix
            )
        },
    )
}


