package br.com.arduino.home.interfaces

import br.com.arduino.home.bean.RowBean

interface listenerItens {
    var items: List<RowBean>
    fun notifyDataSetChanged()
}