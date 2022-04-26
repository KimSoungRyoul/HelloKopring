package com.example.hellokopring.domain

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "newsfeed")
class NewsFeed(
    val title: String,
    val contents: String,
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "user_id")
    val createdBy: User,
    val receiverId: Long,
    val approval: Boolean
) : BaseEntity()
