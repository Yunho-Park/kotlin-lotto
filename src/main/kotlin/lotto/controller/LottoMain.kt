package lotto.controller

import lotto.domain.Lotto
import lotto.domain.LottoManager
import lotto.domain.LottoNumber
import lotto.inputToInt
import lotto.tokenizeWinningNumbers
import lotto.view.Input
import lotto.view.Output

fun main() {
    // 구입 금액 입력
    val purchased = inputToInt(Input().getPurchaseAmount())

    // 수동 구매 개수 입력
    val manualCount = inputToInt(Input().getManualCount())

    // 로또 매니저 생성
    val lottoManager = LottoManager(purchased, manualCount)

    // 수동 번호 입력
    val manualNumbers = Input().getManualNumbers(manualCount)

    // 로또 발급
    lottoManager.setLottos(manualNumbers.map { Lotto(tokenizeWinningNumbers(it)) })

    // 발급된 로또 안내 출력
    Output().printLottoList(lottoManager.lottos.lottoList, manualCount)

    // 당첨 번호 입력
    val winningNumbers = Input().getWinningNumbers()

    // 당첨 번호 등록
    Lotto(tokenizeWinningNumbers(winningNumbers)).let { lottoManager.setWinningLotto(it) }

    // 보너스 번호 입력
    val bonusNumber = Input().getBonusNumber()

    // 보너스 번호 등록
    lottoManager.setBonusNumber(LottoNumber.from(bonusNumber.toInt()))

    // 당첨 로또 집계
    lottoManager.aggregate()

    // 통계 출력
    Output().printResult(lottoManager.prizes)

    // 수익률 출력
    Output().printEarningRate(lottoManager.prizes, lottoManager.purchased)
}
