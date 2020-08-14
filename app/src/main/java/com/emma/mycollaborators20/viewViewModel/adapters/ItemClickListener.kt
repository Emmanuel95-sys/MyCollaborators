package com.emma.mycollaborators20.viewViewModel.adapters

interface ItemClickListener<T> {
    fun onClick(listObject :  T, position: Int )
}