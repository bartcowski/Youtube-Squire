# Authorization implementation

### Methods:
* API Key - allows for readonly access which is sufficient for most functionalities
* OAuth 2.0 - user needs to go through the auth flow but has access to restricted resources

### Base Idea:
1. User is asked whether they only need readonly access or require more than that. Based on this decision the app sets authorization method for every request made.
2. While OAuth flow doesn't require different users to provide anything apart from clicking "Authorize" in their browser, readonly flow using API Key would probably require such user to provide their key in some way or another.
3. API Key authorization requires "key" parameter in an URL of the request whereas OAuth 2.0 requires "Authorization" header added.
4. There is an abstract class/interface of an authorizationSupplier, its concrete implementation is set after the user's decision. It then modifies request accordingly.
5. User is able to change how the app authorizes requests by entering specific command, e.g. "relog". The app's flow then goes back to the beginning.
