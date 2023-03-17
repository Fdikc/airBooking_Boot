import request from '@/utils/request'
import qs from 'qs'

export function getData(url,data) {
    return request({
        url: url + '?' + qs.stringify(data, { indices: false }),
        method: 'get'
    })
}
export function getDataNoData(url) {
    return request({
        url: url,
        method: 'get'
    })
}
export function postData(url,data) {
    return request({
        url: url,
        method: 'post',
        data: data
    })
}

export function putData(url,data) {
    return request({
        url: url,
        method: 'put',
        data: data
    })
}

export function delData(url,data) {
    return request({
        url: url,
        method: 'DELETE',
        data: data
    })
}

export default { getData, postData, putData, delData,getDataNoData }