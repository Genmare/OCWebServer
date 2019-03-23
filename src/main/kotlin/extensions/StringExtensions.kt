package ocwebserver.extensions

val alignCenter = { str:String -> "<p style=\"text-align: center\">$str</p>"}
val bold = { str:String -> "<b>$str</b>"}


fun String.decoHTML(balise: (String) -> String) = balise(this)

//fun String.formatHTML() = decoHTML(alignCenter)

