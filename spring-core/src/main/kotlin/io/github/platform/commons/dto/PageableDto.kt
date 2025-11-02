package io.github.platform.commons.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

/**
 * Custom pageable response DTO with standardized JSON property names.
 * Extends Spring's PageImpl to provide a consistent pagination structure.
 *
 * @param T Type of elements in the page
 */
class PageableDto<T> : PageImpl<T> {
    constructor(content: List<T>, pageable: Pageable, total: Long) : super(content, pageable, total)

    constructor(content: List<T>) : super(content)

    @JsonProperty("total_pages")
    override fun getTotalPages(): Int = super.getTotalPages()

    @JsonProperty("total_elements")
    override fun getTotalElements(): Long = super.getTotalElements()

    @JsonProperty("page")
    override fun getNumber(): Int = super.getNumber()

    @JsonProperty("per_page")
    override fun getSize(): Int = super.getSize()

    @JsonProperty("number_of_elements")
    override fun getNumberOfElements(): Int = super.getNumberOfElements()

    @JsonIgnore
    override fun getSort(): org.springframework.data.domain.Sort = super.getSort()

    @JsonIgnore
    override fun isLast(): Boolean = super.isLast()

    @JsonIgnore
    override fun isFirst(): Boolean = super.isFirst()

    @JsonIgnore
    override fun getPageable(): Pageable = super.getPageable()

    @JsonIgnore
    override fun isEmpty(): Boolean = super.isEmpty()
}
