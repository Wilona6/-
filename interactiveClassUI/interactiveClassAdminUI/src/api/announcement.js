import request from '@/utils/request'

export function getAnnouncementById(announcementId) {
  return request({
    url: `/announcement/getById/${announcementId}`,
    method: 'get'
  })
}

export function listPage(data) {
  return request({
    url: '/announcement/listPage',
    method: 'get',
    data
  })
}

export function saveOrUpdate(data) {
  return request({
    url: '/announcement/saveOrUpdate',
    method: 'post',
    data
  })
}

export function deleteById(data) {
  console.log(data,'data')
  return request({
    url: `/announcement/delById/${data}`,
    method: 'post'
  })
}

