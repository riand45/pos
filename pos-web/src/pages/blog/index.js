import React from "react"
import Layout from "../../components/layout"

const BlogIndex = () => {
  const posts = [
    { title: "Aplikasi Kasir untuk Cafe: Tingkatkan Kecepatan Pelayanan", slug: "kasir-cafe", date: "14 Feb 2026", excerpt: "Pelajari bagaimana sistem POS yang tepat dapat membantu operasional cafe Anda menjadi lebih efisien." },
    { title: "Manajemen Stok Toko Kelontong Agar Tidak Selisih", slug: "stok-toko-kelontong", date: "12 Feb 2026", excerpt: "Tips praktis mengelola stok barang di toko kelontong menggunakan aplikasi kasir android." },
    { title: "Bisnis Laundry Makin Rapi dengan Catatan Digital", slug: "kasir-laundry", date: "10 Feb 2026", excerpt: "Beralih dari nota manual ke sistem kasir digital untuk bisnis laundry Anda." }
  ]

  return (
    <Layout title="Blog & Edukasi">
      <section className="py-24 bg-background-dark min-h-screen">
        <div className="max-w-7xl mx-auto px-6">
          <div className="flex flex-col md:flex-row md:items-end justify-between gap-8 mb-16">
            <div className="space-y-4">
              <h1 className="text-4xl lg:text-6xl font-black text-white">Blog & <span className="text-primary">Edukasi Bisnis</span></h1>
              <p className="text-xl text-slate-400">Tips, trik, dan panduan mendalam untuk mengembangkan bisnis UMKM Anda.</p>
            </div>
            <div className="flex gap-4">
              <button className="px-6 py-2 rounded-full border border-white/10 text-white text-sm hover:bg-white/5 transition-all">Terbaru</button>
              <button className="px-6 py-2 rounded-full border border-white/10 text-white text-sm hover:bg-white/5 transition-all">Tips UMKM</button>
            </div>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-10">
            {posts.map((post, i) => (
              <div key={i} className="group cursor-pointer">
                <div className="relative aspect-video rounded-3xl overflow-hidden mb-6 border border-white/10 group-hover:border-primary/50 transition-all">
                  <div className="absolute inset-0 bg-slate-800 flex items-center justify-center">
                    <span className="material-symbols-outlined text-6xl text-white/10">{post.slug === 'kasir-cafe' ? 'local_cafe' : post.slug === 'stok-toko-kelontong' ? 'store' : 'local_laundry_service'}</span>
                  </div>
                  <div className="absolute inset-0 bg-gradient-to-t from-background-dark/80 to-transparent"></div>
                  <span className="absolute bottom-4 left-4 bg-primary text-background-dark text-[10px] font-black uppercase tracking-widest px-3 py-1 rounded-full">{post.date}</span>
                </div>
                <h3 className="text-2xl font-bold text-white mb-4 group-hover:text-primary transition-all leading-tight">{post.title}</h3>
                <p className="text-slate-400 mb-6 line-clamp-2 leading-relaxed">{post.excerpt}</p>
                <a href={`/blog/${post.slug}`} className="inline-flex items-center gap-2 text-primary font-bold group-hover:gap-4 transition-all">
                  Baca Selengkapnya
                  <span className="material-symbols-outlined text-sm">arrow_forward</span>
                </a>
              </div>
            ))}
          </div>
        </div>
      </section>
    </Layout>
  )
}

export default BlogIndex
