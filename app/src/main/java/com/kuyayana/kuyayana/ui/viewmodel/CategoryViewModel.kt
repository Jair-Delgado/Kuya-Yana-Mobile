package com.kuyayana.kuyayana.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuyayana.kuyayana.data.models.Category
import com.kuyayana.kuyayana.data.repository.CategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {
    private val repository = CategoryRepository()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    init {
        getCategories()
    }
    fun addCategory(category: Category) {
        viewModelScope.launch {
            try {
                repository.addCategory(category)
                Log.d("CategoryViewModel", "Category added successfully")
                getCategories()
            } catch (e: Exception) {
                Log.e("CategoryViewModel", "Error adding category", e)
            }
        }
    }

     fun getCategories() {
        viewModelScope.launch {
            try {
                _categories.value = repository.getCategories()
                Log.d("CategoryViewModel", "Categories retrieved successfully")
            } catch (e: Exception) {
                Log.e("CategoryViewModel", "Error getting categories", e)
            }
        }
    }
}
