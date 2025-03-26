
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private PermissionService permissionService; // 注入PermissionService

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 获取请求头中的Authorization字段
        String authorizationHeader = request.getHeader("Authorization");

        // 检查Authorization字段是否存在且以Bearer开头
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // 提取JWT令牌
            String token = authorizationHeader.substring(7);
            try {
                // 解析JWT令牌以获取声明信息
                Claims claims = jwtConfig.parseToken(token);
                // 从声明中获取用户ID
                String userId = claims.getSubject();
                // 根据用户ID加载用户权限信息
                Collection<? extends GrantedAuthority> authorities = permissionService.getUserAuthorities(userId);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null, authorities);
                // 设置认证信息的详细信息
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // 将认证信息设置到SecurityContextHolder中
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                // 如果解析JWT令牌失败，设置响应状态码为401 Unauthorized
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                // 返回TOKEN_INVALID的错误信息
                response.getWriter().write(ApiResponse.fail(TOKEN_INVALID).toString());
                return;
            }
        }

        // 继续执行过滤器链
        filterChain.doFilter(request, response);
    }
}