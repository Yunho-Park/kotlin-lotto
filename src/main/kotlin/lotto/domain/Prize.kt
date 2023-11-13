package lotto.domain

enum class Prize(val prize: Int, val match: Int) {
    FIRST(2000000000, 6),
    SECOND(30000000, 5),
    THIRD(1500000, 5),
    FOURTH(50000, 4),
    FIFTH(5000, 3),
    NO_PRIZE(0, -1);

    companion object {
        private const val SECOND_THIRD_MATCH = 5
        private val prizeMap = Prize.values().associateBy { it.match }

        fun getResult(lottos: Lottos, winningLotto: Lotto, bonus: LottoNumber): List<Prize> {
            return lottos.lottoList.map {
                getPrize(it, winningLotto, bonus)
            }
        }
        fun getPrize(lotto: Lotto, winningLotto: Lotto, bonus: LottoNumber): Prize {
            val matchNum = lotto.matches(winningLotto)

            return if (matchNum == SECOND_THIRD_MATCH) {
                if (lotto.numbers.contains(bonus)) SECOND else THIRD
            } else {
                prizeMap[matchNum] ?: NO_PRIZE
            }
        }
    }
}
