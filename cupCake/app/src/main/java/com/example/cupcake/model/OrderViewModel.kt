 package com.example.cupcake.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private const val COST_PER_CUPCAKE = 2.00
private const val COST_FOR_SAME_DAY_PICKUP = 3.00

class OrderViewModel: ViewModel() {
    private val _quantity = MutableLiveData<Int>()
    val quantity:LiveData<Int> = _quantity

    private val _flavor = MutableLiveData<String>()
    val flavor:LiveData<String> = _flavor

    private val _date = MutableLiveData<String>()
    val date:LiveData<String> = _date

    private val _price = MutableLiveData<Double>()
    val price: LiveData<String> = Transformations.map(_price){
        NumberFormat.getCurrencyInstance().format(it)
    }

    val dateOptions = getPickupOptions()

    init {
        resetOrder()
    }

    fun setQuantity(numberCupcakes: Int) {
        _quantity.value = numberCupcakes
        updatePrice()
    }

    fun setFlavor(desiredFlavor: String) {
        _flavor.value = desiredFlavor
    }

    fun setDate(pickupDate: String) {
        _date.value = pickupDate
        updatePrice()
    }

    /*
    * Check if these is any flavor
     */
    fun hasNoFlavorSet(): Boolean {
        return _flavor.value.isNullOrEmpty()
    }

    /*
    * generate pickup dates
    */
    private fun getPickupOptions(): List<String> {
        val options = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calender = Calendar.getInstance()
        repeat(4){
            options.add(formatter.format(calender.time))
            calender.add(Calendar.DATE, 1)
        }
        return options
    }

    /*
    * Update the total Price
     */
    private fun updatePrice(){
        var calculatedPrice = (quantity.value ?: 0)* COST_PER_CUPCAKE
        if(dateOptions[0] == _date.value){
            calculatedPrice += COST_FOR_SAME_DAY_PICKUP
        }
        _price.value = calculatedPrice
    }

    /*
    * Resets the order
     */
    fun resetOrder(){
        _quantity.value=0
        _date.value=""
        _flavor.value=dateOptions[0]
        _price.value=0.0
    }
}
