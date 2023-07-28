package com.github.bartcowski.util

import spock.lang.Specification

class RestUtilsTest extends Specification {

    def "should properly add parameters to given URL without any params"() {
        given:
        def url = "http://localhost"
        def params = [
                "key1": "val1",
                "key2": "val2",
                "key3": "val3",
        ]

        when:
        def urlWithParams = RestUtils.addParamsToUrl(url, params)

        then:
        urlWithParams == "http://localhost?key1=val1&key2=val2&key3=val3"
    }

    def "should properly add parameters to given URL that already has params"() {
        given:
        def url = "http://localhost?key1=val1&key2=val2"
        def params = [
                "key3": "val3",
        ]

        when:
        def urlWithParams = RestUtils.addParamsToUrl(url, params)

        then:
        urlWithParams == "http://localhost?key1=val1&key2=val2&key3=val3"
    }

}
