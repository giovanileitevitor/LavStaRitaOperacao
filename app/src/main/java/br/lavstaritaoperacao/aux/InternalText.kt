package br.lavstaritaoperacao.aux

import br.lavstaritaoperacao.domain.model.Item
import br.lavstaritaoperacao.domain.model.Service

internal fun returnText(service: Service, items: List<Item>): String{
    val stringBuilder = StringBuilder()
    for (item in items) {
        stringBuilder
            .append(item.name)
            .append("[R] qtd: ")
            .append(item.qtd)
            .append("\n")
    }

    return "[L]\n" +
            "[C]<u><font size='big'>LAV STA RITA</font></u>\n" +
            "[L]\n" +
            "[C]================================\n" +
            "[L]${stringBuilder.toString()}" +
//            "[L]  + Size : S\n" +
//            "[L]\n" +
//            "[L]<b>AWESOME HAT</b>[R]24.99e\n" +
//            "[L]  + Size : 57/58\n" +
//            "[L]\n" +
//            "[C]--------------------------------\n" +
//            "[R]TOTAL PRICE :[R]34.98e\n" +
//            "[R]TAX :[R]4.23e\n" +
//            "[L]\n" +
            "[C]================================\n" +
            "[L]Orçamento: [R]${service.price}\n" +
            "[L]\n" +
            "[L]<font size='tall'>Cliente :</font>\n" +
            "[L]${service.clientName}\n" +
            "[L]Fone: ${service.clientPhone}\n" +
            "[L]DataIn: ${service.dataIn}\n" +
            "[L]\n" +
            "[C]<barcode type='ean13' height='10'>831254784551</barcode>\n" +
            "[C]<qrcode size='20'>https://dantsu.com/</qrcode>" +
            "[L]\n" +
            "[L]\n"
}

/*
"[L]<b>Camiseta branca</b>[R]9.99e\n" +
            "[L]<b>Camiseta preta</b>[R]9.99e\n" +
            "[L]<b>Camiseta amarela - 1x</b>[R]9.99e\n" +
            "[L]<b>Calça jeans - 1x</b>[R]9.99e\n" +
            "[L]<b>Camiseta amarela</b>[R]9.99e\n" +
 */