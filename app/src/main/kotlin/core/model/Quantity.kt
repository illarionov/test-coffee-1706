package com.example.coffe1706.core.model

@JvmInline
value class Quantity(val value: Int) {
    init {
        check(value >= 0)
    }

    override fun toString(): String = value.toString()
}
