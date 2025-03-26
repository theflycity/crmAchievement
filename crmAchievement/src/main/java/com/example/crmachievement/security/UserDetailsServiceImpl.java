
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户信息的逻辑
        // CrmUser user = userService.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // 使用PermissionService查询用户权限
        Collection<? extends GrantedAuthority> authorities = permissionService.getUserAuthorities(user.getId());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}