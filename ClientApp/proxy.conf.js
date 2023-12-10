const PROXY_CONFIG = [
  {
    context: [
   ],
    target: "http://localhost:8081",
    secure: false,
    headers: {
      Connection: 'Keep-Alive'
    }
  }
]

module.exports = PROXY_CONFIG;
