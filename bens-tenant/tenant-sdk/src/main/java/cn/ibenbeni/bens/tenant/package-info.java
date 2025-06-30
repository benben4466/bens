/**
 * <p>多租户模块-SDK实现：</p>
 * <p>1. DB层：基于MyBatis-Plus框架实现的多租户功能。</p>
 * <p>2. Web层：在HTTP API请求时，解析请求头中tenant-id的值（租户编号），添加到租户上下文中。</p>
 * <p>注意：若SDK层在后续实现中逐渐变大，可将其继续分拆。</p>
 */
package cn.ibenbeni.bens.tenant;