package com.example.coffe1706.core.model

@JvmInline
public value class Quantity(public val value: Int) {
    init {
        check(value >= 0)
    }

    override fun toString(): String = value.toString()
}
