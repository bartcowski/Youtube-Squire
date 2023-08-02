# IDEAS TO DO or TO RETHINK

1. implement the rest of oauth flow + where and how to store access tokens?
2. Where to put the accessToken getting flow? (Maybe this whole authorizationModeService.setMode(); should be reworked?)
3. should chosen auth method be stored in AuthorizationSupplier?
4. path/params etc. should be STORED inside RestRequestBuilder. That way we can chain its methods in any order. Now if we use "params" before "path" we will fail.
   1. this may also improve request authorization flow
   2. that way we would be able to add .body method instead of putting it into POST()