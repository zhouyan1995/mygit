/**
 * 
 */
package com.nndims.disaster.product.domain;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.nndims.disaster.product.domain.base.ABBaseDto;

/**
 * @author muxl
 * @date 2018年4月10日
 * @time 下午5:48:49
 */
@XmlRootElement
@JsonInclude(Include.NON_EMPTY)
public class ReporterDto extends ABBaseDto {

	private static final long serialVersionUID = -5249170923339627372L;

}
