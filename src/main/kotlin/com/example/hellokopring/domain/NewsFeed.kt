package com.example.hellokopring.domain

import javax.persistence.*

@Entity
@Table(name = "newsfeed")
data class NewsFeed(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    val title: String,
    val contents: String,
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "user_id")
    val createdBy: User,
    val receiverId: Long,
    val approval: Boolean
) : BaseEntity()