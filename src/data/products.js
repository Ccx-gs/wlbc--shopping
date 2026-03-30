export const products = [
  {
    id: 1,
    name: 'iPhone 15 Pro',
    category: 'smartphone',
    price: 8999,
    stock: 50,
    image: null,
    description: 'The iPhone 15 Pro features a titanium design with the powerful A17 Pro chip. Experience the ultimate iPhone with a customizable Action Button, USB-C connectivity, and a pro camera system with 48MP main sensor.',
    specs: {
      display: '6.1" Super Retina XDR OLED',
      processor: 'A17 Pro',
      storage: '256GB',
      camera: '48MP main + 12MP ultra-wide + 12MP 3x telephoto',
      battery: '3274mAh'
    }
  },
  {
    id: 2,
    name: 'Samsung Galaxy S24 Ultra',
    category: 'smartphone',
    price: 9999,
    stock: 35,
    image: null,
    description: 'Samsung Galaxy S24 Ultra redefines what a smartphone can do. Powered by Snapdragon 8 Gen 3, featuring an integrated S Pen, a 200MP camera and 6.8" Dynamic AMOLED display with 120Hz refresh rate.',
    specs: {
      display: '6.8" Dynamic AMOLED 2X, 120Hz',
      processor: 'Snapdragon 8 Gen 3',
      storage: '256GB',
      camera: '200MP main + 12MP ultra-wide + 50MP telephoto',
      battery: '5000mAh'
    }
  },
  {
    id: 3,
    name: 'Xiaomi 14 Pro',
    category: 'smartphone',
    price: 5999,
    stock: 60,
    image: null,
    description: 'Xiaomi 14 Pro brings Leica co-engineered cameras to the forefront with the Snapdragon 8 Gen 3 chipset. Boasts a 50MP Summilux lens system, 120W HyperCharge, and a brilliant 6.73" LTPO AMOLED display.',
    specs: {
      display: '6.73" LTPO AMOLED, 120Hz',
      processor: 'Snapdragon 8 Gen 3',
      storage: '256GB',
      camera: '50MP Leica Summilux + 50MP ultra-wide + 50MP telephoto',
      battery: '4880mAh'
    }
  },
  {
    id: 4,
    name: 'Google Pixel 8 Pro',
    category: 'smartphone',
    price: 7499,
    stock: 28,
    image: null,
    description: 'Google Pixel 8 Pro is powered by Google Tensor G3 with 7 years of OS updates guaranteed. Features the best computational photography with Magic Eraser, Best Take, and Call Screen powered by on-device AI.',
    specs: {
      display: '6.7" LTPO OLED, 1-120Hz',
      processor: 'Google Tensor G3',
      storage: '256GB',
      camera: '50MP main + 48MP ultra-wide + 48MP 5x telephoto',
      battery: '5050mAh'
    }
  },
  {
    id: 5,
    name: 'MacBook Pro 14"',
    category: 'laptop',
    price: 18999,
    stock: 20,
    image: null,
    description: 'MacBook Pro 14" with M3 Pro chip delivers extraordinary performance for demanding workflows. With up to 22 hours of battery life, a stunning Liquid Retina XDR display, and pro connectivity including HDMI and SD card slot.',
    specs: {
      display: '14.2" Liquid Retina XDR',
      processor: 'Apple M3 Pro',
      storage: '512GB SSD',
      memory: '18GB Unified Memory',
      battery: 'Up to 22 hours'
    }
  },
  {
    id: 6,
    name: 'ThinkPad X1 Carbon Gen 11',
    category: 'laptop',
    price: 12999,
    stock: 15,
    image: null,
    description: 'Lenovo ThinkPad X1 Carbon Gen 11 is the world\'s lightest 14" business ultrabook at just 1.12kg. Built for enterprise with military-grade durability, Intel Evo certification, and all-day battery life up to 15 hours.',
    specs: {
      display: '14" IPS anti-glare, 2.8K',
      processor: 'Intel Core i7-1365U',
      storage: '512GB NVMe SSD',
      memory: '16GB LPDDR5',
      battery: 'Up to 15 hours'
    }
  },
  {
    id: 7,
    name: 'Dell XPS 15',
    category: 'laptop',
    price: 13999,
    stock: 18,
    image: null,
    description: 'Dell XPS 15 with OLED display delivers breathtaking visuals with a 3.5K OLED touchscreen. Powered by Intel Core i7 HX series and NVIDIA RTX 4060, perfect for creative professionals and content creators.',
    specs: {
      display: '15.6" 3.5K OLED Touch',
      processor: 'Intel Core i7-13700H',
      storage: '512GB NVMe SSD',
      memory: '16GB DDR5',
      battery: 'Up to 13 hours'
    }
  },
  {
    id: 8,
    name: 'Huawei MateBook X Pro',
    category: 'laptop',
    price: 9999,
    stock: 22,
    image: null,
    description: 'Huawei MateBook X Pro is an ultra-slim premium laptop featuring a stunning 14.2" 3.1K OLED display with true-to-life colors. With NearLink multi-device collaboration and an ultra-light 1.26kg chassis.',
    specs: {
      display: '14.2" 3.1K OLED, 90Hz',
      processor: 'Intel Core i7-1360P',
      storage: '1TB NVMe SSD',
      memory: '16GB LPDDR5',
      battery: 'Up to 14 hours'
    }
  },
  {
    id: 9,
    name: 'iPad Pro 12.9"',
    category: 'tablet',
    price: 8999,
    stock: 40,
    image: null,
    description: 'iPad Pro 12.9" with M2 chip is the ultimate iPad experience. Features a stunning Liquid Retina XDR display with ProMotion 120Hz, Thunderbolt connectivity, and Apple Pencil hover functionality.',
    specs: {
      display: '12.9" Liquid Retina XDR',
      processor: 'Apple M2',
      storage: '256GB',
      connectivity: 'Wi-Fi 6E + 5G optional',
      battery: 'Up to 10 hours'
    }
  },
  {
    id: 10,
    name: 'Xiaomi Pad 6 Pro',
    category: 'tablet',
    price: 2999,
    stock: 45,
    image: null,
    description: 'Xiaomi Pad 6 Pro is a productivity powerhouse with Snapdragon 8+ Gen 1, a stunning 11" 144Hz display, and up to 8800mAh battery. Supports stylus and keyboard accessories for a complete laptop-like experience.',
    specs: {
      display: '11" IPS LCD, 144Hz',
      processor: 'Snapdragon 8+ Gen 1',
      storage: '256GB',
      connectivity: 'Wi-Fi 6E',
      battery: '8800mAh'
    }
  },
  {
    id: 11,
    name: 'AirPods Pro 2nd Gen',
    category: 'accessory',
    price: 1999,
    stock: 100,
    image: null,
    description: 'AirPods Pro 2nd generation features the H2 chip for up to 2x more Active Noise Cancellation than before. With Adaptive Audio that seamlessly blends ANC and Transparency mode, and personalized Spatial Audio.',
    specs: {
      driver: 'Apple H2 chip',
      anc: 'Active Noise Cancellation',
      battery: 'Up to 6 hours (30 with case)',
      connectivity: 'Bluetooth 5.3',
      waterResistance: 'IP54'
    }
  },
  {
    id: 12,
    name: 'Sony WH-1000XM5',
    category: 'accessory',
    price: 2599,
    stock: 75,
    image: null,
    description: 'Sony WH-1000XM5 headphones deliver industry-leading noise cancellation with 8 microphones and two processors. Enjoy up to 30 hours of battery life with Quick Charge, crystal-clear hands-free calling, and stunning sound quality.',
    specs: {
      driver: '30mm drivers',
      anc: 'Industry-leading ANC, 8 microphones',
      battery: 'Up to 30 hours',
      connectivity: 'Bluetooth 5.2, 3.5mm',
      waterResistance: 'Splash-proof'
    }
  }
]
