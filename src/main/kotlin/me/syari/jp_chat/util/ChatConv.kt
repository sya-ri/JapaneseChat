package me.syari.jp_chat.util

import java.io.FileNotFoundException
import java.net.URL
import java.net.URLEncoder

object ChatConv {
    fun convJapanese(target: String) = convGoogleIME(convKana(target))

    private val Table = mapOf(
        "" to arrayOf("あ", "い", "う", "え", "お"),
        "k" to arrayOf("か", "き", "く", "け", "こ"),
        "s" to arrayOf("さ", "し", "す", "せ", "そ"),
        "t" to arrayOf("た", "ち", "つ", "て", "と"),
        "n" to arrayOf("な", "に", "ぬ", "ね", "の"),
        "h" to arrayOf("は", "ひ", "ふ", "へ", "ほ"),
        "m" to arrayOf("ま", "み", "む", "め", "も"),
        "y" to arrayOf("や", "い", "ゆ", "いぇ", "よ"),
        "r" to arrayOf("ら", "り", "る", "れ", "ろ"),
        "w" to arrayOf("わ", "うぃ", "う", "うぇ", "を"),
        "g" to arrayOf("が", "ぎ", "ぐ", "げ", "ご"),
        "z" to arrayOf("ざ", "じ", "ず", "ぜ", "ぞ"),
        "j" to arrayOf("じゃ", "じ", "じゅ", "じぇ", "じょ"),
        "d" to arrayOf("だ", "ぢ", "づ", "で", "ど"),
        "b" to arrayOf("ば", "び", "ぶ", "べ", "ぼ"),
        "p" to arrayOf("ぱ", "ぴ", "ぷ", "ぺ", "ぽ"),
        "gy" to arrayOf("ぎゃ", "ぎぃ", "ぎゅ", "ぎぇ", "ぎょ"),
        "gw" to arrayOf("ぐぁ", "ぐぃ", "ぐぅ", "ぐぇ", "ぐぉ"),
        "zy" to arrayOf("じゃ", "じぃ", "じゅ", "じぇ", "じょ"),
        "jy" to arrayOf("じゃ", "じぃ", "じゅ", "じぇ", "じょ"),
        "dy" to arrayOf("ぢゃ", "ぢぃ", "ぢゅ", "ぢぇ", "ぢょ"),
        "dh" to arrayOf("でゃ", "でぃ", "でゅ", "でぇ", "でょ"),
        "dw" to arrayOf("どぁ", "どぃ", "どぅ", "どぇ", "どぉ"),
        "by" to arrayOf("びゃ", "びぃ", "びゅ", "びぇ", "びょ"),
        "py" to arrayOf("ぴゃ", "ぴぃ", "ぴゅ", "ぴぇ", "ぴょ"),
        "v" to arrayOf("ゔぁ", "ゔぃ", "ゔ", "ゔぇ", "ゔぉ"),
        "vy" to arrayOf("ゔゃ", "ゔぃ", "ゔゅ", "ゔぇ", "ゔょ"),
        "sh" to arrayOf("しゃ", "し", "しゅ", "しぇ", "しょ"),
        "sy" to arrayOf("しゃ", "し", "しゅ", "しぇ", "しょ"),
        "c" to arrayOf("か", "し", "く", "せ", "こ"),
        "ch" to arrayOf("ちゃ", "ち", "ちゅ", "ちぇ", "ちょ"),
        "cy" to arrayOf("ちゃ", "ち", "ちゅ", "ちぇ", "ちょ"),
        "f" to arrayOf("ふぁ", "ふぃ", "ふ", "ふぇ", "ふぉ"),
        "fy" to arrayOf("ふゃ", "ふぃ", "ふゅ", "ふぇ", "ふょ"),
        "fw" to arrayOf("ふぁ", "ふぃ", "ふ", "ふぇ", "ふぉ"),
        "q" to arrayOf("くぁ", "くぃ", "く", "くぇ", "くぉ"),
        "ky" to arrayOf("きゃ", "きぃ", "きゅ", "きぇ", "きょ"),
        "kw" to arrayOf("くぁ", "くぃ", "く", "くぇ", "くぉ"),
        "ty" to arrayOf("ちゃ", "ちぃ", "ちゅ", "ちぇ", "ちょ"),
        "ts" to arrayOf("つぁ", "つぃ", "つ", "つぇ", "つぉ"),
        "th" to arrayOf("てゃ", "てぃ", "てゅ", "てぇ", "てょ"),
        "tw" to arrayOf("とぁ", "とぃ", "とぅ", "とぇ", "とぉ"),
        "ny" to arrayOf("にゃ", "にぃ", "にゅ", "にぇ", "にょ"),
        "hy" to arrayOf("ひゃ", "ひぃ", "ひゅ", "ひぇ", "ひょ"),
        "my" to arrayOf("みゃ", "みぃ", "みゅ", "みぇ", "みょ"),
        "ry" to arrayOf("りゃ", "りぃ", "りゅ", "りぇ", "りょ"),
        "l" to arrayOf("ぁ", "ぃ", "ぅ", "ぇ", "ぉ"),
        "x" to arrayOf("ぁ", "ぃ", "ぅ", "ぇ", "ぉ"),
        "ly" to arrayOf("ゃ", "ぃ", "ゅ", "ぇ", "ょ"),
        "lt" to arrayOf("た", "ち", "っ", "て", "と"),
        "lk" to arrayOf("ゕ", "き", "く", "ゖ", "こ"),
        "xy" to arrayOf("ゃ", "ぃ", "ゅ", "ぇ", "ょ"),
        "xt" to arrayOf("た", "ち", "っ", "て", "と"),
        "xk" to arrayOf("ゕ", "き", "く", "ゖ", "こ"),
        "wy" to arrayOf("わ", "ゐ", "う", "ゑ", "を"),
        "wh" to arrayOf("うぁ", "うぃ", "う", "うぇ", "うぉ")
    )

    private fun convKana(org: String): String {
        fun getKanaFromTable(s: String, n: Int): String {
            return Table[s]?.get(n) ?: s + Table[""]?.get(n)
        }

        fun getVowel(s: String): Int {
            return when(s) {
                "a" -> 0
                "i" -> 1
                "u" -> 2
                "e" -> 3
                "o" -> 4
                else -> - 1
            }
        }

        var last = ""
        val line = StringBuilder()

        for(i in org.indices) {
            val tmp = org.substring(i, i + 1)
            val vowel = getVowel(tmp)
            if(vowel != - 1) {
                if(Table[last] != null) {
                    line.append(getKanaFromTable(last, vowel))
                } else {
                    line.append(last.substring(0, 1) + getKanaFromTable(last.substring(1, 2), vowel))
                }
                last = ""
            } else {
                if(last == "n" && tmp != "y") {
                    line.append("ん")
                    last = ""
                    if(tmp == "n") {
                        continue
                    }
                }
                if(Character.isLetter(tmp[0])) {
                    last = when {
                        Character.isUpperCase(tmp[0]) -> {
                            line.append(last + tmp)
                            ""
                        }
                        last == tmp -> {
                            line.append("っ")
                            tmp
                        }
                        else -> last + tmp
                    }
                } else {
                    line.append(
                        last + when(tmp) {
                            "-" -> "ー"
                            "." -> "。"
                            "," -> "、"
                            "?" -> "？"
                            "!" -> "！"
                            "[" -> "「"
                            "]" -> "」"
                            "&" -> "＆"
                            else -> tmp
                        }
                    )
                    last = ""
                }
            }
        }
        line.append(last)

        return line.toString()
    }

    private fun convGoogleIME(s: String): String {
        val url: URL
        val bef: String
        try {
            url = URL("https://www.google.com/transliterate?langpair=ja-Hira|ja&text=" + URLEncoder.encode(s, "utf-8"))
            bef = url.readText()
        } catch(ex: FileNotFoundException) {
            return s
        }
        val aft = StringBuilder()
        var level = 0
        var index = 0
        while(index < bef.length) {
            if(level < 3) {
                val nextStart = bef.indexOf("[", index)
                val nextEnd = bef.indexOf("]", index)
                index = when {
                    nextStart == - 1 -> return aft.toString()
                    nextStart < nextEnd -> {
                        level ++
                        nextStart + 1
                    }
                    else -> {
                        level --
                        nextEnd + 1
                    }
                }
            } else {
                val start = bef.indexOf("\"", index)
                val end = bef.indexOf("\"", start + 1)
                if(start == - 1 || end == - 1) return aft.toString()
                aft.append(bef.substring(start + 1, end))
                val next = bef.indexOf("]", end)
                if(next == - 1) {
                    return aft.toString()
                } else {
                    level --
                    index = next + 1
                }
            }
        }
        return aft.toString()
    }
}