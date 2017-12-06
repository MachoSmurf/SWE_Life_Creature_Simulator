package UserPackage;

public class UserController implements IUserController {
    @Override
    public boolean Login(String username, String password) {
        return false;
    }

    @Override
    public void Logout() {

    }

    @Override
    public boolean getRights() {
        return false;
    }
}
