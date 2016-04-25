package com.tinet.ctilink.mapper;

import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.Marker;
import tk.mybatis.mapper.common.RowBoundsMapper;
import tk.mybatis.mapper.common.SqlServerMapper;
import tk.mybatis.mapper.common.base.BaseDeleteMapper;
import tk.mybatis.mapper.common.base.BaseSelectMapper;
import tk.mybatis.mapper.common.base.BaseUpdateMapper;

/**
 * @author Jiangsl
 *
 * add by fengwei
 * PostgreSQL insert时不支持主键是null的情况, 所以没有继承tk.mybatis.mapper.common.BaseMapper
 * 分别继承了BaseSelectMapper, BaseUpdateMapper, BaseDeleteMapper和SqlServerMapper
 *
 * http://git.oschina.net/free/Mapper/blob/master/wiki/mapper3/4.Professional.md
 * http://git.oschina.net/free/Mapper/blob/master/wiki/mapper3/5.Mappers.md
 */

public interface BaseMapper<T> extends BaseSelectMapper<T>, BaseUpdateMapper<T>, BaseDeleteMapper<T>, SqlServerMapper<T>,
        ConditionMapper<T>, RowBoundsMapper<T>, Marker {

}
