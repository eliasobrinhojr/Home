package br.com.arduino.home.bean

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity
data class RowBean(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "url") var url: String,
    @ColumnInfo(name = "descricao") var descricao: String,
    @ColumnInfo(name = "ativo") var ativo: String
): Serializable