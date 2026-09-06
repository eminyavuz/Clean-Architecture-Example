(function () {
  const STORAGE_PRODUCT = "cleanArch.lastProductId";
  const STORAGE_ORDER = "cleanArch.lastOrderId";

  const els = {
    baseUrl: document.getElementById("baseUrl"),
    responseStatus: document.getElementById("response-status"),
    responseBody: document.getElementById("response-body"),
    requestLog: document.getElementById("request-log"),
    lastProduct: document.getElementById("lastProductId"),
    lastOrder: document.getElementById("lastOrderId"),
    pageTitle: document.getElementById("page-title"),
    consoleDrawer: document.getElementById("console-drawer"),
    toast: document.getElementById("toast"),
    productsBody: document.getElementById("products-table-body"),
    productsBadge: document.getElementById("products-count-badge"),
    ordersBody: document.getElementById("orders-table-body"),
    ordersBadge: document.getElementById("orders-count-badge"),
    statProducts: document.getElementById("stat-product-count"),
    statOrders: document.getElementById("stat-order-count"),
    statLastOrder: document.getElementById("stat-last-order"),
    flowResult: document.getElementById("flow-result"),
    orderDetailPlaceholder: document.getElementById("order-detail-placeholder"),
    orderDetailContent: document.getElementById("order-detail-content"),
    detailOrderId: document.getElementById("detail-order-id"),
    detailOrderStatus: document.getElementById("detail-order-status"),
    detailOrderTotal: document.getElementById("detail-order-total"),
    detailOrderItems: document.getElementById("detail-order-items"),
  };

  const titles = { dashboard: "Dashboard", products: "Products", orders: "Orders" };

  function baseUrl() {
    const v = els.baseUrl.value.trim();
    return v.endsWith("/") ? v.slice(0, -1) : v;
  }

  function showToast(message, type) {
    els.toast.textContent = message;
    els.toast.className = "toast " + (type || "");
    els.toast.hidden = false;
    clearTimeout(showToast._t);
    showToast._t = setTimeout(() => { els.toast.hidden = true; }, 3000);
  }

  function rememberProductId(id) {
    if (!id || id <= 0) return;
    localStorage.setItem(STORAGE_PRODUCT, String(id));
    els.lastProduct.textContent = id;
    document.querySelectorAll('input[name="productId"]').forEach((el) => {
      if (!el.value) el.value = id;
    });
  }

  function rememberOrderId(id) {
    if (!id || id <= 0) return;
    localStorage.setItem(STORAGE_ORDER, String(id));
    els.lastOrder.textContent = id;
    els.statLastOrder.textContent = id;
    document.querySelectorAll('input[name="orderId"]').forEach((el) => {
      if (!el.value) el.value = id;
    });
  }

  function initRemembered() {
    const p = localStorage.getItem(STORAGE_PRODUCT);
    const o = localStorage.getItem(STORAGE_ORDER);
    if (p) {
      els.lastProduct.textContent = p;
      document.querySelectorAll('input[name="productId"]').forEach((el) => {
        if (!el.value) el.value = p;
      });
    }
    if (o) {
      els.lastOrder.textContent = o;
      els.statLastOrder.textContent = o;
      document.querySelectorAll('input[name="orderId"]').forEach((el) => {
        if (!el.value) el.value = o;
      });
    }
  }

  function appendLog(method, path, status) {
    const entry = document.createElement("div");
    entry.className = "log-entry";
    entry.textContent = `${new Date().toLocaleTimeString()} ${method} ${path} → ${status}`;
    els.requestLog.prepend(entry);
  }

  function parseResponseBody(text) {
    if (!text) return null;
    try {
      return JSON.parse(text);
    } catch {
      const n = Number(text);
      return Number.isFinite(n) && text.trim() === String(n) ? n : text;
    }
  }

  async function api(method, path, body, options = {}) {
    const { silent = false, showConsole = false } = options;
    const url = baseUrl() + path;
    const fetchOptions = {
      method,
      headers: { Accept: "application/json" },
    };
    if (body !== undefined) {
      fetchOptions.headers["Content-Type"] = "application/json";
      fetchOptions.body = JSON.stringify(body);
    }

    const res = await fetch(url, fetchOptions);
    const text = await res.text();
    const parsed = parseResponseBody(text);

    if (!silent || showConsole) {
      els.responseStatus.textContent = String(res.status);
      els.responseStatus.className = "status-pill " + (res.ok ? "ok" : "err");
      els.responseBody.textContent =
        typeof parsed === "object" && parsed !== null
          ? JSON.stringify(parsed, null, 2)
          : String(parsed ?? "(empty)");
      if (showConsole || !res.ok) els.consoleDrawer.classList.add("open");
    }
    appendLog(method, path, res.status);

    if (!res.ok) {
      const msg = parsed?.message || `HTTP ${res.status}`;
      const err = new Error(msg);
      err.status = res.status;
      err.body = parsed;
      throw err;
    }
    return parsed;
  }

  function escapeHtml(s) {
    const d = document.createElement("div");
    d.textContent = s ?? "";
    return d.innerHTML;
  }

  function formatMoney(v) {
    if (v == null) return "—";
    return "$" + Number(v).toFixed(2);
  }

  function renderOrderDetail(order) {
    if (!order) return;
    els.orderDetailPlaceholder.hidden = true;
    els.orderDetailContent.hidden = false;
    els.detailOrderId.textContent = "#" + order.id;
    els.detailOrderStatus.textContent = order.status;
    els.detailOrderTotal.textContent = formatMoney(order.totalPrice);

    const items = order.orderItemResponses || [];
    els.detailOrderItems.innerHTML = items.length
      ? items
          .map(
            (i) =>
              `<li><strong>${escapeHtml(i.productName)}</strong> (product #${i.productId}) × ${i.quantity} = ${formatMoney(i.lineTotal ?? i.unitPrice)}</li>`
          )
          .join("")
      : '<li class="muted">No items yet</li>';
  }

  async function loadOrderDetail(orderId, silent) {
    const order = await api("GET", `/orders/${orderId}`, undefined, { silent: !!silent });
    renderOrderDetail(order);
    return order;
  }

  function renderProductsTable(products) {
    if (!products?.length) {
      els.productsBody.innerHTML =
        '<tr><td colspan="6" class="empty">No products. Create one from the form below.</td></tr>';
      els.productsBadge.textContent = "0 items";
      els.statProducts.textContent = "0";
      return;
    }
    els.productsBadge.textContent = products.length + " items";
    els.statProducts.textContent = products.length;
    els.productsBody.innerHTML = products
      .map(
        (p) => `
      <tr>
        <td><strong>#${p.id}</strong></td>
        <td>${escapeHtml(p.productName)}</td>
        <td>${formatMoney(p.price)}</td>
        <td>${p.stock}</td>
        <td><span class="status-badge ${p.active ? "on" : "off"}">${p.active ? "Active" : "Inactive"}</span></td>
        <td>
          <button type="button" class="btn btn-sm" data-toggle-product="${p.id}" data-active="${p.active}">
            ${p.active ? "Deactivate" : "Activate"}
          </button>
        </td>
      </tr>`
      )
      .join("");

    els.productsBody.querySelectorAll("[data-toggle-product]").forEach((btn) => {
      btn.addEventListener("click", async () => {
        const id = btn.dataset.toggleProduct;
        const active = btn.dataset.active === "true";
        try {
          await api("PUT", `/products/${id}/${active ? "deactivate" : "activate"}`, undefined, { silent: true });
          showToast("Product updated", "success");
          await loadProducts();
        } catch (e) {
          showToast(e.message, "error");
        }
      });
    });
  }

  async function loadProducts(silent) {
    const products = await api("GET", "/products", undefined, { silent: !!silent });
    renderProductsTable(products);
    return products;
  }

  function renderOrdersTable(orders) {
    if (!orders?.length) {
      els.ordersBody.innerHTML =
        '<tr><td colspan="5" class="empty">No orders. Click “Create order”.</td></tr>';
      els.ordersBadge.textContent = "0 items";
      els.statOrders.textContent = "0";
      return;
    }
    els.ordersBadge.textContent = orders.length + " items";
    els.statOrders.textContent = orders.length;
    els.ordersBody.innerHTML = orders
      .map((o) => {
        const itemCount = (o.orderItemResponses || []).length;
        return `
      <tr>
        <td><strong>#${o.id}</strong></td>
        <td><span class="status-badge on">${escapeHtml(o.status)}</span></td>
        <td>${itemCount}</td>
        <td>${formatMoney(o.totalPrice)}</td>
        <td><button type="button" class="btn btn-sm" data-view-order="${o.id}">View</button></td>
      </tr>`;
      })
      .join("");

    els.ordersBody.querySelectorAll("[data-view-order]").forEach((btn) => {
      btn.addEventListener("click", () => {
        const id = parseInt(btn.dataset.viewOrder, 10);
        rememberOrderId(id);
        loadOrderDetail(id).catch((e) => showToast(e.message, "error"));
      });
    });
  }

  async function loadOrders(silent) {
    const orders = await api("GET", "/orders", undefined, { silent: !!silent });
    renderOrdersTable(orders);
    return orders;
  }

  function switchView(name) {
    document.querySelectorAll(".nav-item").forEach((b) => {
      b.classList.toggle("active", b.dataset.view === name);
    });
    document.querySelectorAll(".view").forEach((v) => {
      v.classList.toggle("active", v.id === "view-" + name);
    });
    els.pageTitle.textContent = titles[name] || name;
    if (name === "products") loadProducts(true).catch(() => {});
    if (name === "orders") {
      loadOrders(true).catch(() => {});
      const oid = localStorage.getItem(STORAGE_ORDER);
      if (oid) loadOrderDetail(parseInt(oid, 10), true).catch(() => {});
    }
    if (name === "dashboard") {
      loadProducts(true).catch(() => {});
      loadOrders(true).catch(() => {});
    }
  }

  async function createOrder() {
    const orderId = await api("POST", "/orders/create", undefined, { silent: true });
    const id = typeof orderId === "number" ? orderId : parseInt(orderId, 10);
    if (!id || id <= 0) throw new Error("Order created but invalid id returned: " + orderId);
    rememberOrderId(id);
    return id;
  }

  async function runFullFlowTest() {
    const log = [];
    const step = (msg) => {
      log.push(msg);
      els.flowResult.textContent = log.join("\n");
    };

    try {
      step("1/4 Creating product…");
      const product = await api("POST", "/products/create", {
        productName: "Flow " + Date.now(),
        description: "Automated UI flow test product",
        price: 25.5,
        stock: 15,
        isActive: true,
      }, { silent: true });
      if (!product?.id) throw new Error("Product id missing in response");
      rememberProductId(product.id);
      step("   ✓ Product #" + product.id + " created (stock " + product.stock + ")");

      step("2/4 Creating order…");
      const orderId = await createOrder();
      step("   ✓ Order #" + orderId + " created");

      step("3/4 Adding product to order…");
      const orderAfterAdd = await api(
        "POST",
        `/orders/${orderId}/items`,
        { productId: product.id, quantity: 2 },
        { silent: true }
      );
      step("   ✓ Item added. Order total: " + formatMoney(orderAfterAdd.totalPrice));

      step("4/4 Verifying product & order…");
      const productAfter = await api("GET", `/products/${product.id}`, undefined, { silent: true });
      const orderAfter = await api("GET", `/orders/${orderId}`, undefined, { silent: true });

      if (productAfter.stock !== product.stock - 2) {
        throw new Error("Stock not decreased correctly. Expected " + (product.stock - 2) + ", got " + productAfter.stock);
      }
      if (!orderAfter.orderItemResponses?.length) {
        throw new Error("Order has no line items after add");
      }

      renderOrderDetail(orderAfter);
      await loadProducts(true);
      await loadOrders(true);

      step("   ✓ Product stock now: " + productAfter.stock);
      step("   ✓ Order has " + orderAfter.orderItemResponses.length + " line item(s)");
      step("\n✅ Full flow passed!");
      showToast("Full flow test passed", "success");
      switchView("orders");
    } catch (e) {
      step("\n❌ Failed: " + (e.message || e));
      showToast(e.message || "Flow failed", "error");
      els.consoleDrawer.classList.add("open");
    }
  }

  document.querySelectorAll(".nav-item").forEach((btn) => {
    btn.addEventListener("click", () => switchView(btn.dataset.view));
  });

  document.getElementById("btn-toggle-console").addEventListener("click", () => {
    els.consoleDrawer.classList.toggle("open");
  });
  document.getElementById("btn-close-console").addEventListener("click", () => {
    els.consoleDrawer.classList.remove("open");
  });
  document.getElementById("btn-clear-log").addEventListener("click", () => {
    els.requestLog.innerHTML = "";
    els.responseBody.textContent = "Log cleared.";
  });

  document.getElementById("btn-run-full-flow").addEventListener("click", runFullFlowTest);
  document.getElementById("btn-refresh-products").addEventListener("click", () => {
    loadProducts().then(() => showToast("Products refreshed", "success")).catch((e) => showToast(e.message, "error"));
  });
  document.getElementById("btn-refresh-orders").addEventListener("click", () => {
    loadOrders().then(() => showToast("Orders refreshed", "success")).catch((e) => showToast(e.message, "error"));
  });
  document.getElementById("btn-show-create").addEventListener("click", () => {
    document.getElementById("create-product-card").scrollIntoView({ behavior: "smooth" });
  });

  document.getElementById("form-create-product").addEventListener("submit", async (e) => {
    e.preventDefault();
    const fd = new FormData(e.target);
    try {
      const result = await api("POST", "/products/create", {
        productName: fd.get("productName"),
        description: fd.get("description"),
        price: parseFloat(fd.get("price")),
        stock: parseInt(fd.get("stock"), 10),
        isActive: e.target.querySelector('[name="isActive"]').checked,
      });
      rememberProductId(result.id);
      showToast("Product #" + result.id + " created", "success");
      await loadProducts(true);
    } catch (err) {
      showToast(err.message, "error");
    }
  });

  document.getElementById("btn-create-order").addEventListener("click", async () => {
    try {
      const id = await createOrder();
      showToast("Order #" + id + " created", "success");
      await loadOrders(true);
      await loadOrderDetail(id, true);
    } catch (e) {
      showToast(e.message, "error");
    }
  });

  document.getElementById("form-add-to-order").addEventListener("submit", async (e) => {
    e.preventDefault();
    const fd = new FormData(e.target);
    const orderId = parseInt(fd.get("orderId"), 10);
    const productId = parseInt(fd.get("productId"), 10);
    rememberOrderId(orderId);
    rememberProductId(productId);
    try {
      const order = await api("POST", `/orders/${orderId}/items`, {
        productId,
        quantity: parseInt(fd.get("quantity"), 10),
      });
      renderOrderDetail(order);
      showToast("Product added to order #" + orderId, "success");
      await loadProducts(true);
      await loadOrders(true);
    } catch (err) {
      showToast(err.message, "error");
    }
  });

  document.getElementById("form-start-order").addEventListener("submit", async (e) => {
    e.preventDefault();
    const orderId = parseInt(new FormData(e.target).get("orderId"), 10);
    rememberOrderId(orderId);
    try {
      const order = await api("POST", `/orders/${orderId}/start`);
      renderOrderDetail(order);
      showToast("Order #" + orderId + " in progress", "success");
      await loadOrders(true);
    } catch (err) {
      showToast(err.message, "error");
    }
  });

  initRemembered();
  switchView("dashboard");
})();
