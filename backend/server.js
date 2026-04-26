import express from 'express'
import cors from 'cors'
import fs from 'node:fs'
import path from 'node:path'
import { fileURLToPath } from 'node:url'

const __filename = fileURLToPath(import.meta.url)
const __dirname = path.dirname(__filename)
const dataFile = path.join(__dirname, 'data.json')

const app = express()
const PORT = 3000
const FRONTEND_URL = process.env.FRONTEND_URL || 'http://localhost:5173'
const DEFAULT_PAGE_SIZE = 8
const MAX_PAGE_SIZE = 50

app.use(cors({ origin: FRONTEND_URL }))
app.use(express.json())

const products = JSON.parse(fs.readFileSync(dataFile, 'utf-8'))
let cart = []

function normalizeCartItem(item) {
  const productId = Number(item.id)
  const product = products.find((p) => p.id === productId)
  if (!product) return null

  const skuId = item.skuId ? String(item.skuId) : null
  const sku = skuId ? product.skus.find((s) => s.id === skuId) : product.skus[0]
  if (!sku) return null

  const quantity = Math.max(1, Number(item.quantity) || 1)
  const cartKey = `${product.id}-${sku.id}`

  return {
    cartKey,
    id: product.id,
    skuId: sku.id,
    skuName: sku.name,
    name: product.name,
    brand: product.brand,
    category: product.category,
    image: product.images?.[0] || product.image,
    price: sku.price,
    quantity,
  }
}

app.get('/api/products', (req, res) => {
  const category = String(req.query.category || '').trim()
  const search = String(req.query.search || '').trim().toLowerCase()
  const page = Number(req.query.page)
  const pageSize = Number(req.query.pageSize)
  const usePagination = Number.isFinite(page) || Number.isFinite(pageSize)

  const filtered = products.filter((item) => {
    const categoryOk = !category || category === 'all' || item.category === category
    const searchOk = !search
      || item.name.toLowerCase().includes(search)
      || item.brand.toLowerCase().includes(search)
      || item.description.toLowerCase().includes(search)
    return categoryOk && searchOk
  })

  if (!usePagination) {
    return res.json(filtered)
  }

  const normalizedPageSize = Math.max(
    1,
    Math.min(MAX_PAGE_SIZE, Number.isFinite(pageSize) ? Math.floor(pageSize) : DEFAULT_PAGE_SIZE),
  )
  const total = filtered.length
  const totalPages = Math.max(1, Math.ceil(total / normalizedPageSize))
  const normalizedPage = Math.min(totalPages, Math.max(1, Number.isFinite(page) ? Math.floor(page) : 1))
  const start = (normalizedPage - 1) * normalizedPageSize
  const list = filtered.slice(start, start + normalizedPageSize)
  const categories = [...new Set(products.map((item) => item.category))]

  res.json({
    list,
    total,
    page: normalizedPage,
    pageSize: normalizedPageSize,
    totalPages,
    categories,
  })
})

app.get('/api/products/:id', (req, res) => {
  const id = Number(req.params.id)
  const product = products.find((item) => item.id === id)
  if (!product) {
    return res.status(404).json({ message: 'Product not found' })
  }
  res.json(product)
})

app.get('/api/cart', (_req, res) => {
  res.json(cart)
})

app.post('/api/cart', (req, res) => {
  const input = Array.isArray(req.body) ? req.body : [req.body]

  if (!input.length) {
    cart = []
    return res.json(cart)
  }

  const mapped = input
    .map(normalizeCartItem)
    .filter(Boolean)

  cart = mapped
  res.json(cart)
})

app.post('/api/orders', (_req, res) => {
  const orderId = `ORD-${Date.now()}`
  cart = []
  res.json({ success: true, orderId })
})

app.listen(PORT, () => {
  console.log(`Backend server running at http://localhost:${PORT}`)
})
