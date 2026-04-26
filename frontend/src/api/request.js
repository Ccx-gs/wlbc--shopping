import http from './http.js'

async function request(url, config = {}) {
  const response = await http({
    url,
    method: config.method || 'get',
    params: config.params,
    data: config.data,
  })

  return response.data
}

export default request
