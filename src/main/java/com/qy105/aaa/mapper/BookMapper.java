package com.qy105.aaa.mapper;


import com.qy105.aaa.model.Book;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
//@CacheNamespace(implementation = com.qy105.aaa.redis.RedisCache.class)
public interface BookMapper {
    @Select("select * from book_info")
    List<Book> getAllBook();
    @Insert("insert into book_info (book_name,book_store,book_price) value(#{bookName},#{bookStore},#{bookPrice})")
    void addBook(Book book);
    @Update("update book_info set book_name=#{bookName},book_store = #{bookStore},book_price = #{bookPrice} where book_id = #{bookId}")
    void updateBook(Book book);
    @Delete("delete from book_info where book_id=#{bookId}")
    void delBook(int id);
}
