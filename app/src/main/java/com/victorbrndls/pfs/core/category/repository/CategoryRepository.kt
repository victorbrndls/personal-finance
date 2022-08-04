package com.victorbrndls.pfs.core.category.repository

import com.victorbrndls.pfs.core.category.dto.EditCategoryData
import com.victorbrndls.pfs.core.category.entity.Category

interface CategoryRepository {
    suspend fun getAll(): List<Category>
    suspend fun save(expense: EditCategoryData)
}