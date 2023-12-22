# Automatic HTTP Requests (AutoHTTPRequester)

A program designed to execute HTTP Requests on a scheduler like Windows Task Scheduler.

## Dynamic Update Client (DUC)

I created this program to act as a DUC for my domain ```hombre.asia```. My ISP switched to IPV6, so no out-of-the-box and
free IPV6 DUC clients exists out there. In the process, I realized that Cloudflare(my DNS provider) has an API for
modifying my DNS Records.

## Details

Compile it using Gradle 8.2 ```gradle jar``` and Kotlin 1.9.21. Then create a ```http_options.json``` file. See the included example. Afterward,
run it using ```java -jar AutoHTTPRequester-0.0.2.jar``` and make sure ```http_options.json``` is in the same directory.

## Future Updates?

Maybe. I made this because I could and for a specific use case. Please make a pull request if you want to add changes.

## License (Apache 2.0)

```text
Copyright 2023 Ron Lauren Hombre

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
