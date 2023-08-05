# IDEAS TO DO or TO RETHINK

1. access token file should be secured in some way (encryption?)
2. problem: without certain (top-secret) .properties file app cannot be run (can user provide his own apikey? Where to store clientId and clientSecret so that anyone running this app can at least successfully go through OAuth flow?)
3. caches (straight into service OR another layer between service and restClient)
4. better exception handling
5. ability to export results to files? especially useful when retrieving hundreds of comments
6. paging for terminal printing - so that we are not flooded with data
7. should chosen auth method be stored in AuthorizationSupplier?
8. path/params etc. should be STORED inside RestRequestBuilder. That way we can chain its methods in any order. Now if we use "params" before "path" we will fail.
   1. this may also improve request authorization flow
   2. that way we would be able to add .body method instead of putting it into POST()
9. Implementing extended functionality for OAuth mode - user should be able to do way more than just query data