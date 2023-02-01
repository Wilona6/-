import { get, post } from '@/utils/request'

export function listPage(params) {
  return get('/paper/listPage', params)
}
export function addQuestion(params) {
  return post('/paperQuestion/addQuestion', params)
}
export function queryPaperQuestionById(params) {
  return get('/paperQuestion/queryPaperQuestionById', params)
}
export function delPaper(params) {
  return post('/paper/delPaper', params)
}

export function deletepaper(params) {
  return post('/paper/deletepaper', params)
}
export function createPaper(params) {
  return post('/paper/createPaper', params)
}

export function testStudent(params) {
  return post('/testSituation/listPage', params)
}
