package com.example.onlineshopping

class DataModel {
    var name: String? = null
    var price: String? = null
    var image: String? = null
    var id : String? = null

    fun getIds(): String {
        return id.toString()
    }

    fun setIds(id: String) {
        this.id = id
    }

    fun getNames(): String {
        return name.toString()
    }

    fun setNames(name: String) {
        this.name = name
    }

    fun getPrices(): String {
        return price.toString()
    }

    fun setPrices(price: String) {
        this.price = price
    }

    fun getImages(): String {
        return image.toString()
    }

    fun setImages(image: String) {
        this.image = image
    }


}

//data class Product(val name: String, val imageUrl: String, val price: String)